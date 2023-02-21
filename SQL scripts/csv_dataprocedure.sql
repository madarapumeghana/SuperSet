CREATE OR REPLACE PROCEDURE POPULATECSV_DATA (P_DATE date, P_FILENAME varchar) LANGUAGE PLPGSQL AS $$

DECLARE
   filePath varchar(1000);
   row_count int;

BEGIN
   filePath=concat('/home/meghana/Documents/Shairu_semicolon_files/Files/semicolon_files/',to_char(p_date,'dd_mm_yyyy'),'/',p_fileName);

   if p_fileName = 'mfg_data.csv' then
       delete from mfg_data_tmp;
       execute format('copy mfg_data_tmp(Sr,Kapan_No,Packet_No,Pcs,Exp_Wgt,Lab,Pointer,Shape,Color,Purity,Cut,Fls,Plan_Value,last_modifiedby,last_modified_date) from %L with delimiter '','' quote ''"'' csv header', filePath);
	   select count(*) into row_count from mfg_data where last_modified_date = p_date;
	   if(row_count <> 0) then
	    delete from mfg_data where last_modified_date = p_date;
	   end if;
	   insert into mfg_data select * from mfg_data_tmp where last_modified_date = p_date;

	elsif p_fileName = 'tsr_data.csv' then
	   delete from tsr_data_tmp;
	   execute format('copy tsr_data_tmp from %L with delimiter '','' quote ''"'' csv header', filePath);
	   select count(*) into row_count from tsr_data where ason = p_date;
	   if(row_count <> 0) then
	    delete from tsr_data where ason = p_date;
	   end if;
	   insert into tsr_data select * from tsr_data_tmp where ason = p_date;

	elsif p_fileName = 'lab_data.csv' then
	   delete from lab_data_tmp;
	   execute format('copy lab_data_tmp from %L with delimiter '','' quote ''"'' csv header', filePath);
	   select count(*) into row_count from lab_data where inv_date = p_date;
	   if(row_count <> 0) then
	    delete from lab_data where inv_date = p_date;
	   end if;
	   insert into lab_data select * from lab_data_tmp where inv_date = p_date;

    elsif p_fileName = 'purchase_data.csv' then
	   delete from purchase_data_tmp;
	   execute format('copy purchase_data_tmp(Sr,Inv_Date,Ref_No,inw_shp_grp,Shape,Cts,Pointer,Color,Clarity,Cut,Polish,Symm,Fls,Width,Length,Depth,Depth_per,Due_Date,Rap_Price,Rap_Value,Pur_Disc_per,Pur_Value,Supplier) from %L with delimiter '','' quote ''"'' csv header', filePath);
	   select count(*) into row_count from purchase_data where inv_date = p_date;
	   if(row_count <> 0) then
	    delete from purchase_data where inv_date = p_date;
	   end if;
	   insert into purchase_data select * from purchase_data_tmp where inv_date = p_date;
	   update purchase_data set last_modifiedby=1,last_modified_date = now() where inv_date = p_date;

	elsif p_fileName = 'sale_data.csv' then
	   delete from sale_data_tmp;
	   execute format('copy sale_data_tmp(Sr,Ref_No,Shape,inw_shp_grp,Cts,Pointer,Color,Clarity,Lab,Cut,Polish,Symm,Fls,fls_color,Width,Length,Depth,Depth_per,Crown_Angle,Crown_Height,crown_incl,Crown_Natts,Crown_Open,Girdle,Girdle_per,girdle_cond,Luster,Milky_Grade,Shade,user_comments,Comments,inscription,Symbol,Pav_Angle,Pav_Height,Table_per,table_incl,Table_Natts,Table_Open,table_natts_type,table_incl_type,website,ratio,lab_charge,Rap_Price,Rap_Value,Final_Disc_per,Price_Cts,Net_Value,Wgt_Avg_per,wgt_sale_value,Pur_Rap_Price,Pur_Rap_Value,Pur_Disc_per,Pur_Value,Profit,Profit_per,int_disc,Offer_per,Offer_Diff,Offer_Value,dom_value,final_cost,Days,lab_days,Inv_No,"Date",Inv_Date,Process,Terms,Customer,Country,Supplier) from %L with delimiter '','' quote ''"'' csv header', filePath);
	execute format('call populateSale_Data (%L,%L)',p_date,p_date);
    update sale_data set last_modifiedby=1,last_modified_date = now() where inv_date = p_date;
	
	else
	   execute format('copy SuratAndBackPrice FROM %L with DELIMITER '','' quote ''"'' csv header', filePath);

	end if;

end $$;