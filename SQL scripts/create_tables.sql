CREATE TABLE sale_data (
  Sr bigint,
  Ref_No varchar(30) CONSTRAINT sale_data_pk PRIMARY KEY,
  Shape varchar(30),
  inw_shp_grp varchar(20),
  Cts numeric,
  Pointer varchar(40),
  Color varchar(20),
  Clarity varchar(20),
  Lab varchar(20),
  Cut varchar(20),
  Polish varchar(20),
  Symm varchar(20),
  Fls varchar(20),
  fls_color varchar(20),
  Width numeric,
  Length numeric,
  Depth numeric,
  Depth_per numeric,
  Crown_Angle numeric,
  Crown_Height numeric,
  crown_incl varchar(20),
  Crown_Natts varchar(20),
  Crown_Open varchar(20),
  Girdle varchar(50),
  Girdle_per numeric,
  girdle_cond varchar(30),
  Luster varchar(30),
  Milky_Grade varchar(30),
  Shade varchar(30),
  user_comments varchar(50),
  Comments varchar(200),
  inscription varchar(3),
  Symbol varchar(100),
  Pav_Angle numeric,
  Pav_Height numeric,
  Table_per numeric,
  table_incl varchar(10),
  Table_Natts varchar(20),
  Table_Open varchar(20),
  table_natts_type varchar(20),
  table_incl_type varchar(10),
  website varchar(2),
  ratio numeric,
  lab_charge numeric,
  Rap_Price numeric,
  Rap_Value numeric,
  Final_Disc_per numeric,
  Price_Cts numeric,
  Net_Value numeric,
  Wgt_Avg_per numeric,
  wgt_sale_value numeric,
  Pur_Rap_Price numeric,
  Pur_Rap_Value numeric,
  Pur_Disc_per numeric,
  Pur_Value numeric,
  Profit numeric,
  Profit_per numeric,
  int_disc numeric,
  Offer_per numeric,
  Offer_Diff numeric,
  Offer_Value numeric,
  dom_value numeric,
  final_cost numeric,
  Days numeric,
  lab_days numeric,
  Inv_No varchar(50),
  "Date" Date,
  Inv_Date Date,
  Process varchar(50),
  Terms varchar(50),
  Customer varchar(50),
  Country varchar(50),
  Supplier varchar(50),
  last_modifiedby smallint,
  last_modified_date timestamp,
  AnalysisPointer varchar(20),
  UC varchar(10),
  MonthNo int, 
  Month varchar(20),
  Year int,
  Qauter varchar(20),
  Website_1 varchar(20),
  Bullseye numeric(10,2),
  Diff numeric(10,2),
  Bullseye_1 varchar(20),
  Bullseye_2 varchar(20),
  Diff1 numeric(10,2),
  SIValue numeric(10,2),
  Customer_1 varchar(100),
  Category varchar(20),
  SrtPointer varchar(20),
  MonthNoInYear smallint,
  Month1 varchar(12),
  Purchase_Pcs int,
  Pcs int,
  QTR1 varchar(20),
  Size varchar(30),
  SRTPointer_shapewise  varchar(30),
  Con_1 varchar(50),
  Con_2 varchar(50),
  Rap_Pointer varchar(20),
  Con_4  varchar(20),
  Cuts varchar(50),
  srt numeric,
  srt_price numeric
);


CREATE TABLE purchase_data (
  Sr bigint,
  inw_shp_grp varchar(30),
  Ref_No varchar(30) CONSTRAINT purchase_data_pk PRIMARY KEY,
  Shape varchar(50),
  Cts numeric,
  Color varchar(30),
  Clarity varchar(30),
  Cut varchar(30),
  Polish varchar(30),
  Symm varchar(30),
  Fls varchar(30),
  Width numeric,
  Length numeric,
  Depth numeric,
  Depth_per numeric,
  Due_Date Date,
  Rap_Value numeric,
  Rap_Price numeric,
  Pur_Value numeric,
  Pur_Disc_per numeric,
  Pointer varchar(30),
  Supplier varchar(100),
  Inv_Date Date,
  last_modifiedby smallint,
  last_modified_date timestamp
);


CREATE TABLE tsr_data (
--id SERIAL PRIMARY KEY,
Sr bigint,
Process varchar(50),
cons_in_stock varchar(1),
Issue_Date Date,
Shape varchar(50),
Ref_No varchar(50),
Kapan_No varchar(90),
Color varchar(30),
Clarity varchar(30),
Cts varchar(30),
Pointer varchar(50),
Lab varchar(30),
Cut varchar(30),
Polish varchar(30),
Symm varchar(30),
Fls varchar(30),
Length numeric,
Width numeric,
Depth numeric,
Depth_per numeric,
Table_per numeric,
Crown_Angle numeric,
Crown_Height numeric,
crown_incl varchar(40),
crown_incl_type varchar(40),
crown_natts varchar(40),
crown_open varchar(40),
Culet varchar(40),
girdle varchar(100),
girdle_cond varchar(40),
girdle_per numeric,
Pav_Angle varchar(50),
Pav_Height varchar(50),
table_incl varchar(50),
table_incl_type varchar(50),
table_open varchar(50),
table_natts varchar(50),
Shade varchar(50),
Luster varchar(50),
member_comments varchar(100),
lab_comments varchar(200),
ratio numeric,
Symbol varchar(200),
Supplier varchar(100),
Customer varchar(100),
Status varchar(10),
Inv_Date Date,
SI_Rap_Rate numeric,
SI_Rap_Value numeric,
SI_Disc_per numeric,
SI_Price_Cts numeric,
SI_Value numeric,
MV_final_cost numeric,
MV_Disc_per numeric,
MV_Value numeric,
MV_Price_Cts numeric,
Cur_Pur_Disc_per numeric,
Cur_Price_Cts numeric,
Cost_Rap_Rate numeric,
Cost_Rap_Value numeric,
Cost_Rate numeric,
Cost_Value numeric,
Cost_Disc_per numeric,
Cost_Net_Value numeric,
Cost_Inw_Process varchar(50),
Cost_lab_date Date,
Cost_lab_days numeric,
Cost_Days numeric,
last_modifiedby smallint,
last_modified_date timestamp,
ason date
);



CREATE TABLE mfg_data (
   id SERIAL PRIMARY KEY,
  Sr bigint,
  Kapan_No text,
  Packet_No numeric,
  Pcs numeric,
  Exp_Wgt numeric,
  Lab varchar(50),
  Pointer varchar(50),
  Shape varchar(50),
  Color varchar(50),
  Purity varchar(50),
  Cut varchar(50),
  Fls varchar(50),
  Plan_Value numeric,
  last_modifiedby smallint,
  last_modified_date timestamp
);


CREATE TABLE lab_data(
   Sr               Bigint
  ,Inv_Date         DATE
  ,Lab              VARCHAR(40) 
  ,Ref_No           VARCHAR(30) CONSTRAINT lab_data_pk PRIMARY KEY
  ,Shape            VARCHAR(50) 
  ,Pointer          VARCHAR(50) 
  ,Cts              NUMERIC
  ,Color            VARCHAR(20) 
  ,Clarity          VARCHAR(40) 
  ,Cut              VARCHAR(30)
  ,Polish           VARCHAR(20) 
  ,Symm             VARCHAR(20) 
  ,Fls              VARCHAR(20) 
  ,Width            NUMERIC 
  ,Length           NUMERIC
  ,Depth            NUMERIC
  ,Depth_           NUMERIC
  ,Customer         VARCHAR(100) 
  ,Process          VARCHAR(100) 
  ,Pur_Rap_Price    NUMERIC 
  ,Pur_Rap_Value    NUMERIC
  ,Crown_Angle      NUMERIC
  ,Crown_Height     NUMERIC
  ,crown_incl       VARCHAR(20) 
  ,crown_incl_type  VARCHAR(20) 
  ,Crown_Natts      VARCHAR(20) 
  ,crown_natts_type VARCHAR(20) 
  ,Crown_Open       VARCHAR(20) 
  ,Pav_Angle        NUMERIC 
  ,Pav_Height       NUMERIC
  ,Pav_Open         VARCHAR(20) 
  ,Table_           NUMERIC
  ,table_incl       VARCHAR(20) 
  ,table_incl_type  VARCHAR(20) 
  ,Table_Open       VARCHAR(20) 
  ,Luster           VARCHAR(20) 
  ,Milky_Grade      VARCHAR(20) 
  ,Shade            VARCHAR(20) 
  ,user_comments    VARCHAR(20) 
  ,Culet            VARCHAR(20) 
  ,Girdle           VARCHAR(200) 
  ,Girdle_per          NUMERIC
  ,girdle_cond      VARCHAR(20) 
  ,Comments         VARCHAR(100)
  ,Symbol           VARCHAR(100)
);

CREATE TABLE SuratAndBackPrice (
  rap_date date,
  shape varchar(30),
  pointer varchar(30),
  clarity varchar(30),
  cut varchar(30),
  color varchar(30),
  fls varchar(30),
  suratPrice numeric,
  backPrice numeric,
  cert_cost numeric,
  font_suratprice varchar(30),
  font_suratcolor_price varchar(30),
  font_back varchar(30),
  font_color_back varchar(30)
);

CREATE TABLE sale_data_tmp (
  Sr bigint,
  Ref_No varchar(30) CONSTRAINT sale_data_pk_tmp PRIMARY KEY,
  Shape varchar(30),
  inw_shp_grp varchar(20),
  Cts numeric,
  Pointer varchar(40),
  Color varchar(20),
  Clarity varchar(20),
  Lab varchar(20),
  Cut varchar(20),
  Polish varchar(20),
  Symm varchar(20),
  Fls varchar(20),
  fls_color varchar(20),
  Width numeric,
  Length numeric,
  Depth numeric,
  Depth_per numeric,
  Crown_Angle numeric,
  Crown_Height numeric,
  crown_incl varchar(20),
  Crown_Natts varchar(20),
  Crown_Open varchar(20),
  Girdle varchar(50),
  Girdle_per numeric,
  girdle_cond varchar(30),
  Luster varchar(30),
  Milky_Grade varchar(30),
  Shade varchar(30),
  user_comments varchar(50),
  Comments varchar(100),
  inscription varchar(3),
  Symbol varchar(100),
  Pav_Angle numeric,
  Pav_Height numeric,
  Table_per numeric,
  table_incl varchar(10),
  Table_Natts varchar(10),
  Table_Open varchar(10),
  table_natts_type varchar(10),
  table_incl_type varchar(10),
  website varchar(2),
  ratio numeric,
  lab_charge numeric,
  Rap_Price numeric,
  Rap_Value numeric,
  Final_Disc_per numeric,
  Price_Cts numeric,
  Net_Value numeric,
  Wgt_Avg_per numeric,
  wgt_sale_value numeric,
  Pur_Rap_Price numeric,
  Pur_Rap_Value numeric,
  Pur_Disc_per numeric,
  Pur_Value numeric,
  Profit numeric,
  Profit_per numeric,
  int_disc numeric,
  Offer_per numeric,
  Offer_Diff numeric,
  Offer_Value numeric,
  dom_value numeric,
  final_cost numeric,
  Days numeric,
  lab_days numeric,
  Inv_No varchar(50),
  "Date" Date,
  Inv_Date Date,
  Process varchar(50),
  Terms varchar(50),
  Customer varchar(50),
  Country varchar(50),
  Supplier varchar(50),
  last_modifiedby smallint,
  last_modified_date timestamp,
  AnalysisPointer varchar(20),
  UC varchar(10),
  MonthNo int, 
  Month varchar(20),
  Year int,
  Qauter varchar(20),
  Website_1 varchar(20),
  Bullseye numeric(10,2),
  Diff numeric(10,2),
  Bullseye_1 varchar(20),
  Bullseye_2 varchar(20),
  Diff1 numeric(10,2),
  SIValue numeric(10,2),
  Customer_1 varchar(100),
  Category varchar(20),
  SrtPointer varchar(20),
  MonthNoInYear smallint,
  Month1 varchar(12),
  Purchase_Pcs int,
  Pcs int,
  QTR1 varchar(20),
  Size varchar(30),
  SRTPointer_shapewise  varchar(30),
  Con_1 varchar(50),
  Con_2 varchar(50),
  Rap_Pointer varchar(20),
  Con_4  varchar(20),
  Cuts varchar(50),
  srt numeric,
  srt_price numeric
);


CREATE TABLE purchase_data_tmp (
  Sr bigint,
  inw_shp_grp varchar(30),
  Ref_No varchar(30) CONSTRAINT purchase_data_pk_tmp PRIMARY KEY,
  Shape varchar(50),
  Cts numeric,
  Color varchar(30),
  Clarity varchar(30),
  Cut varchar(30),
  Polish varchar(30),
  Symm varchar(30),
  Fls varchar(30),
  Width numeric,
  Length numeric,
  Depth numeric,
  Depth_per numeric,
  Due_Date Date,
  Rap_Value numeric,
  Rap_Price numeric,					
  Pur_Value numeric,
  Pur_Disc_per numeric,
  Pointer varchar(30),
  Supplier varchar(100),
  Inv_Date Date,
  last_modifiedby smallint,
  last_modified_date timestamp
);


CREATE TABLE tsr_data_tmp (
--id SERIAL PRIMARY KEY,
Sr bigint,
Process varchar(50),
cons_in_stock varchar(1),
Issue_Date Date,
Shape varchar(50),
Ref_No varchar(50),
Kapan_No varchar(90),
Color varchar(30),
Clarity varchar(30),
Cts varchar(30),
Pointer varchar(50),
Lab varchar(30),
Cut varchar(30),
Polish varchar(30),
Symm varchar(30),
Fls varchar(30),
Length numeric,
Width numeric,
Depth numeric,
Depth_per numeric,
Table_per numeric,
Crown_Angle numeric,
Crown_Height numeric,
crown_incl varchar(40),
crown_incl_type varchar(40),
crown_natts varchar(40),
crown_open varchar(40),
Culet varchar(40),
girdle varchar(100),
girdle_cond varchar(40),
girdle_per numeric,
Pav_Angle varchar(50),
Pav_Height varchar(50),
table_incl varchar(50),
table_incl_type varchar(50),
table_open varchar(50),
table_natts varchar(50),
Shade varchar(50),
Luster varchar(50),
member_comments varchar(100),
lab_comments varchar(200),
ratio numeric,
Symbol varchar(200),
Supplier varchar(100),
Customer varchar(100),
Status varchar(10),
Inv_Date Date,
SI_Rap_Rate numeric,
SI_Rap_Value numeric,
SI_Disc_per numeric,
SI_Price_Cts numeric,
SI_Value numeric,
MV_final_cost numeric,
MV_Disc_per numeric,
MV_Value numeric,
MV_Price_Cts numeric,
Cur_Pur_Disc_per numeric,
Cur_Price_Cts numeric,
Cost_Rap_Rate numeric,
Cost_Rap_Value numeric,
Cost_Rate numeric,
Cost_Value numeric,
Cost_Disc_per numeric,
Cost_Net_Value numeric,
Cost_Inw_Process varchar(50),
Cost_lab_date Date,
Cost_lab_days numeric,
Cost_Days numeric,
last_modifiedby smallint,
last_modified_date timestamp,
ason date
);



CREATE TABLE mfg_data_tmp (
   id SERIAL PRIMARY KEY,
  Sr bigint,
  Kapan_No text,
  Packet_No numeric,
  Pcs numeric,
  Exp_Wgt numeric,
  Lab varchar(50),
  Pointer varchar(50),
  Shape varchar(50),
  Color varchar(50),
  Purity varchar(50),
  Cut varchar(50),
  Fls varchar(50),
  Plan_Value numeric,
  last_modifiedby smallint,
  last_modified_date timestamp
);