Create OR REPLACE PROCEDURE populateSale_Data (p_startdate date,p_enddate date)
LANGUAGE plpgsql AS $$

DECLARE
   reference varchar;
   row_count int;
  
BEGIN
  for reference in select distinct ref_no from sale_data_tmp where Inv_Date between p_startdate and p_enddate
    LOOP
	
	update sale_data_tmp sd1 set AnalysisPointer=sd.AnalysisPointer,
                     UC=sd.UC,
					 "year"=sd."year",
					 "month"=sd."month",
					 qauter=sd.qauter,
					 Bullseye=sd.Bullseye,
					 diff1=sd.diff1,
					 SIValue=sd.SIValue,
					 customer_1=sd.customer_1,
					 category=sd.category,
					 SrtPointer=sd.SrtPointer,
					 MonthNoInYear=sd.MonthNoInYear,
					 Month1=sd.Month1,
					 purchase_pcs=sd.purchase_pcs,
					 pcs=sd.pcs,
					 QTR1=sd.QTR1,
					 Size=sd.Size,
					 Rap_Pointer=sd.Rap_Pointer,
					 SRTPointer_shapewise=sd.SRTPointer_shapewise,
					 cuts=sd.cuts from 
(select 
(select pointers from analysispointer a where sale_data_tmp.cts >= a."from" and sale_data_tmp.cts <= a."to") as AnalysisPointer,
case when user_comments ilike 'No BGM' then 'NO BGM' else 'BGM' end as UC,
extract(year from Inv_Date) as "year",
to_char(Inv_Date,'Mon YY') as "month",
concat(extract(QUARTER from Inv_Date),'Q-',to_char(Inv_Date,'YY')) as qauter,
case when offer_per is not null then (100-((100-offer_per)*0.98)) else null end as Bullseye,
Wgt_Avg_per-offer_per as Diff1,
((100-int_disc)*Rap_Value/100) as SIValue,
(select case when count(validity)=0 then 'Others' else max(validity) end from customermemo c where c.customer ilike sale_data_tmp.customer) as customer_1,
case when cts >= 0.9 then 'Carats' else 'Dossiers' end as category,
(select pointers from suratpointer sptr where sale_data_tmp.cts <= sptr."to" and sale_data_tmp.cts >= sptr."from") as SrtPointer,
extract(month from Inv_Date) as MonthNoInYear,
to_char(Inv_Date,'Mon') as Month1,
(select count(*) from purchase_data where Inv_Date=sale_data_tmp.Inv_Date) as purchase_pcs,
1 as pcs,
concat(extract(quarter from Inv_Date),'Q') as QTR1,
case when cts <= 0.89 then '0.90 below'
     when cts <= 1.99 then '0.90-1.99'
	 when cts >= 2 then '2.00 up'
	 end as Size,
(select srtpointer from suratpointercost sptrcost where sale_data_tmp.shape ilike 
concat('%',(select shapename from shapemaster where shapeid=sptrcost.shapeid),'%') 
and sale_data_tmp.cts between sptrcost."From" and sptrcost."To") as SRTPointer_shapewise,
(select pointer from rapnetpointers r where sale_data_tmp.cts between r."from" and r."to") as Rap_Pointer,
case when cut='3EX' and fls='NON' then '3EX Non' else 'Others' end as cuts 
from sale_data_tmp where ref_no=reference) sd where sd1.ref_no=reference;


update sale_data_tmp set diff=sd.diff,
                     Bullseye_1 = sd.Bullseye_1,
					 Con_1 = sd.Con_1,
					 Con_2 = sd.Con_2,
					 Con_4 = sd.Con_4
from
(select
round((bullseye-Wgt_Avg_per)/0.5,0)*0.5 as diff,
case when Bullseye=Wgt_Avg_per then 'TRUE' else 'FALSE' end as Bullseye_1,
concat(shape,srtpointer_shapewise) as Con_1,
concat(shape,srtpointer_shapewise,color,fls) as Con_2,
case when color in ('N','O-P','Q-R','S-T','U-V','W-X','Y-Z') then concat(Rap_Pointer,'M') else concat(Rap_Pointer,color) end as Con_4 
from sale_data_tmp where ref_no=reference) sd where ref_no=reference;
	
	
update sale_data_tmp set Bullseye_2=sd.Bullseye_2 from (
select case when website='Y' then 'At our price'
     when Bullseye-Final_Disc_per=0 then 'At our price'
	 when customer ilike 'Bluenile' or customer ilike 'James Allen' then 'At our price'
	 when Wgt_Avg_per-offer_per >= 0.5 or Wgt_Avg_per-offer_per <= -0.5 then 'Deal'
	 else 'At our price'
	 end as Bullseye_2 from sale_data_tmp where ref_no=reference) sd where ref_no=reference;
	 
update sale_data_tmp set Website_1=sd.Website_1 
					 from
(select case when Bullseye_2 ilike 'At our price' then 'Online' else 'Offline' end as Website_1
	   from sale_data_tmp where ref_no=reference) sd where ref_no=reference;
	
	end loop;
	
	select count(*) into row_count from sale_data where Inv_Date between p_startdate and p_enddate;
	if(row_count <> 0) then 
	    delete from sale_data where Inv_Date between p_startdate and p_enddate;
	end if;
	insert into sale_data select * from sale_data_tmp where Inv_Date between p_startdate and p_enddate;
	
end $$;