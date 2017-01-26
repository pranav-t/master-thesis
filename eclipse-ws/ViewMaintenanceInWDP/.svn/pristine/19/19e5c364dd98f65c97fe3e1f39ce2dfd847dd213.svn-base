package de.webdataplatform.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.log.Log;
import de.webdataplatform.parser.Column;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.ColumnSequence;
import de.webdataplatform.settings.JoinTablePair;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.DeltaRecord;
import de.webdataplatform.system.PreAggRecord;
import de.webdataplatform.system.ReverseJoinRecord;
import de.webdataplatform.system.TableRecord;
import de.webdataplatform.view.operation.PreAggregation;
import de.webdataplatform.view.operation.ReverseJoin;

public class TestRecordProcessing {

	/**
	 * @param args
	 */
	
	private static Log log;
	

	
	
	public static void main(String[] args) {

		log = new Log("TestClient");
		SystemConfig.load(log);
		NetworkConfig.load(log);

		
		TableRecord tableRecord = TableRecord.generateTableRecord("A", "k1", "c1", "x1", "c2", "10");
		
		TableRecord tableRecordA = TableRecord.generateTableRecord("q1_V1", "l0616", "c1", "x02", "c2", "089");
		TableRecord tableRecordA2 = TableRecord.generateTableRecord("q1_V1", "l0617", "c1", "x02", "c2", "077");
		TableRecord tableRecordB = TableRecord.generateTableRecord("q1_V0", "k39", "d1", "x02", "d2", "018");
		
		
//		TableRecord tableRecordC = generateTableRecord("C", "m1", "c1", "x1", "c2", "10", "c3", "15");
		
		TableRecord tableRecord1 = TableRecord.generateTableRecord("A", "k1", "c1", "x1;x2", "c2", "10;12");
		TableRecord tableRecord2 = TableRecord.generateTableRecord("A", "k1", "c1", "null;x2", "c2", "null;12");
		TableRecord tableRecord3 = TableRecord.generateTableRecord("A", "k1", "c1", "null;x1", "c2", "null;10");
		
		
		
		long startx = System.nanoTime();
		DeltaRecord deltaRecord1 = new DeltaRecord(tableRecord1);
		DeltaRecord deltaRecord2 = new DeltaRecord(tableRecord2);
		DeltaRecord deltaRecord3 = new DeltaRecord(null, new DeltaRecord(tableRecord3).getNewRecord());
		System.out.println("backtrans-time: "+(System.nanoTime()-startx));
		
		System.out.println("deltaRecord1: "+deltaRecord1);
		System.out.println("deltaRecord2: "+deltaRecord2);
		System.out.println("deltaRecord3: "+deltaRecord3);
		
		System.out.println("-------------");
		
		long start0 = System.nanoTime();
		
		System.out.println("deltaRecord1 back: "+deltaRecord1.transform());
		System.out.println("deltaRecord2 back: "+deltaRecord2.transform());
		System.out.println("deltaRecord3 back: "+deltaRecord3.transform());
		
		System.out.println("transform-time: "+(System.nanoTime()-start0));
		
		System.out.println("-------------");
		
		ColumnSequence select = new ColumnSequence();//{"avg1", "sum2","sum3", "count1"};
		select.addColumn(new Column("count(A.c2)", "count"));
		select.addColumn(new Column("sum(A.c2)", "sum1"));
//		select.addColumn(new Column("sum(A.c3)", "sum2"));
//		select.addColumn(new Column("100 + sum(A.c3)", "sum3"));
//		select.addColumn(new Column("count(*)", "count1"));
		
		
		ColumnSequence groupingKeys = new ColumnSequence();//{"A.c1"};
		groupingKeys.addColumn(new Column("A.c1", null));
//		groupingKeys.addColumn(new Column("A.c1", null));

		
		TableRecord tableRecord4 = TableRecord.generateTableRecord("A", "k1", "c1","x1", "c2","14.4");
		
		TableRecord tableRecordNew = TableRecord.generateTableRecord("A", "x1", "c1", "1023108:224578.48;1261283:196636.34;0541921:88677.89;10"
                                    +" 24772:257346.43;0542019:196011.85;0542084:228904.67;1082183:259810.21;1081666:5463.18;0544896:114953.33;2"
                                     +"040069:64505.60;0547041:48057.41;2340832:261883.61;2284486:114582.81;0302435:278856.43;1323779:65830.96;0"
                                     +"551623:110443.87;1087111:165488.80;2282211:97363.15;2101985:117085.39;1086437:117346.70;0550917:231108.02"
                                     +";2343808:48589.79;0363232:165453.09;0364962:12310.99;2284679:33436.66;0306660:72456.99;0364164:75439.42;0"
                                     +"364803:125991.68;1325892:94064.08;2291940:266145.74;0481636:248548.11;0553345:182270.43;0548903:84651.33;"
                                     +"1148806:95805.28;0563011:51922.84;2102914:224763.56;1443366:312351.41;1328229:150917.49;0250272:150683.76"
                                     +";2293249:233443.83;0506148:82131.34;1036102:1770.83;1090919:163806.41;2298727:183044.53;1091399:208782.96"
                                     +";0846337:234168.62;0901063:89843.94;1092134:328718.76;2162789:89580.26;0250695:3741.37;2354880:119819.32;"
                                     +"2109186:219848.61;2355334:239455.07;0901287:181985.12;0562727:100268.85;0561696:38694.16;0486145:202577.8"
                                     +"1;1151749:322081.64;0252326:3651.41;0063140:64603.46;0124608:57312.69;1152611:210528.30;0311495:261483.15"
                                     +";2109062:260626.91;0510273:196670.43;1044419:298181.44;0181602:41094.25;0182759:304118.02;0507047:111507."
                                     +"73;2166403:290644.79;0065252:212962.10;0182980:118283.21;1156100:128411.34;0965090:238760.00;0068417:1282"
                                     +"06.55;1097894:223074.90;1445223:94861.58;0372739:240971.38;2301987:112792.54;2359619:242063.97;0242279:26"
                                     +"5391.42;0253894:75409.07;0846049:210955.36;1045607:117521.86;0574437:174445.97;0523809:14017.06;0187203:6"
                                     +"6506.43;0377284:183863.48;0520837:58334.00;1274082:139186.11;0492961:61487.00;0496483:74539.37;0438982:34"
                                     +"7967.81;0439235:287614.75;0263553:293373.92;2250661:75970.80;0383585:284428.70;2371680:260406.55;2370791:"
                                     +"88584.58;2370887:244136.84;1047778:8114.67;0440517:186291.15;1388260:9534.77;0126375:60251.31;0964516:171"
                                     +"809.19;1108098:301644.09;1055750:225967.49;1048353:269128.59;0326693:199249.74;0067553:292281.09;0906339:"
                                     +"38524.93;2253862:132973.23;2378948:10327.22;2318979:145621.75;2371268:157571.60;2364865:149501.08;1049252"
                                     +":181418.84;1051847:195532.12;1113090:124321.27;1052134:82094.84;1172420:103698.39;1063621:143644.79;09644"
                                     +"80:290219.86;0790630:85565.18;0852519:21142.31;0794181:9607.85;0853510:164825.68;0911650:164634.57;237731"
                                     +"8:102079.12;2320356:95981.19;2254629:156650.24;2369575:204182.39;2376772:153724.82;2256867:76363.66;23815"
                                     +"73:158485.60;1007136:189953.15;1115621:251755.84;1001159:79099.17;1118080:107266.37;1062404:5419.70;11184"
                                     +"02:106180.01;0575365:157270.73;0582758:45773.90;0526243:108812.16;0580800:213931.18;0977537:198033.49;238"
                                     +"6722:248401.96;2261058:200091.98;2259393:103294.87;2381413:105069.32;1008165:190281.49;1063555:94949.95;1"
                                     +"121024:180161.22;1009764:119920.73;1180546:129365.88;1180675:53922.53;0495235:86051.36;0588900:73518.25;0"
                                     +"580773:232152.32;0533991:42049.63;0537859:170636.43;0538242:205805.53;0594695:163958.52;2389189:121995.46"
                                     +";1067333:176455.55;1189987:77348.30;0449922:120909.02;1121890:372674.56;0920164:87090.66;0851845:237704.9"
                                     +"3;0806949:85799.02;1018144:175879.64;1177892:48009.83;2055910:39037.66;1274628:151525.32;0539810:23018.02"
                                     +";0603296:251316.79;2266727:183436.50;2329924:195568.56;0126086:370339.43;1124131:63604.63;1123973:291026."
                                     +"68;1136199:23722.98;0605155:18676.51;2388679:373331.84;2392291:267939.66;0247073:170326.00;1394432:71319."
                                     +"30;0919683:183608.06;0977410:52967.46;1079943:184825.83;1016038:77462.10;1176416:27912.69;0390914:109834."
                                     +"37;0594246:308473.98;0663622:144457.30;2328070:153663.19;2382659:32031.22;1276835:117904.80;0856643:55661"
                                     +".83;0279364:191156.13;0071399:10598.31;0335458:23222.25;2259491:209796.22;1286432:32491.75;1125189:50773."
                                     +"80;2401060:10002.42;0458886:36698.01;0388166:242322.80;0730722:112712.54;0453252:168720.61;0273477:87781."
                                     +"64;0340386:78862.22;0450150:204293.54;1395044:364703.36;1405154:163971.92;1402663:282182.99;1452004:35120"
                                     +"3.75;2470469:223653.74;2474308:106424.05;2004709:216263.67;0401124:186323.09;1282630:91515.57;1282368:142"
                                     +"180.87;0330853:38904.43;0332551:121111.31;1127523:43688.48;1135558:48905.30;2269575:332491.94;0613477:884"
                                     +"29.16;0720705:124265.12;0247366:135959.76;0337987:48300.87;1397219:211120.16;0610468:223748.35;0975810:31"
                                     +"5207.83;0669634:11462.21;2476228:257569.65;0071683:43656.40;1196609:97848.88;0400071:61663.75;0462599:101"
                                     +"312.27;2003621:212769.71;0805767:92810.80;1133479:55817.43;0734146:66662.48;0813892:167351.92;2466944:271"
                                     +"574.87;0289798:281904.37;0454849:301328.23;0726243:149260.26;2232387:243828.76;2469314:87008.95;1287173:2"
                                     +"35643.36;0730496:265944.88;0403104:99621.77;0752486:210732.39;2418306:58837.73;1139008:167325.46;1194688:"
                                     +"125360.58;1069504:221598.05;2120933:125329.01;2399584:134311.71;1204773:143665.60;0281184:31509.11;067411"
                                     +"5:157785.15;1135936:78692.61;1355490:109759.04;0616167:41771.75;2411938:243368.52;2461509:288555.49;07324"
                                     +"49:345505.40;0667841:296459.12;0864581:25827.59;2477508:38235.88;0189895:196573.56;0013026:187752.31;0189"
                                     +"218:100532.84;0013446:43169.18;0733730:88927.65;0721220:223915.66;0614563:286136.37;0923975:151187.15;239"
                                     +"7120:96573.97;1016197:244165.00;0341606:85463.28;0868612:64329.65;1402309:197935.35;1296000:102957.90;246"
                                     +"7200:205885.75;0727138:273942.75;1403074:230029.64;2404068:250106.95;0729060:171682.77;0866724:230713.62;"
                                     +"0994178:219146.25;2477926:113469.80;2422468:209957.07;0618597:225676.48;0615649:299804.89;1412771:138824."
                                     +"15;1474149:142120.87;1300164:345521.08;1406854:150390.68;1410273:239357.03;1424225:149338.29;2413348:2650"
                                     +"62.30;1568932:226766.08;0465955:4804.03;0337862:48569.23;2237445:90259.52;2008515:306116.45;2060387:17063"
                                     +"6.64;2005991:196544.81;2012033:102488.10;0681411:86404.86;0691398:315897.46;0873862:250290.82;1420548:650"
                                     +"82.60;1479968:190375.08;1206050:180621.76;0351846:153356.78;2241989:72352.18;0759590:85569.14;0943845:834"
                                     +"9.49;1367175:82163.20;1300550:123330.55;1314176:164139.19;0470758:224862.17;1479526:100193.68;1431553:177"
                                     +"049.07;1417732:156620.36;1423143:150470.69;1428929:27867.99;1481442:215613.58;1769057:111188.73;0699681:1"
                                     +"66265.91;0080932:218442.34;0140134:227023.54;0081766:256665.56;1308256:141308.71;2273376:202699.36;035507"
                                     +"3:193312.27;2021671:158526.50;0141345:125923.01;0018372:126725.08;0137921:97175.01;2418912:209863.35;2495"
                                     +"586:87380.80;1623140:38189.26;0414854:146181.78;0745697:17898.65;0140389:203348.38;0025699:263391.91;0073"
                                     +"924:161081.61;0766471:269354.13;1742884:236872.79;1767202:14969.29;0284706:78087.58;0884421:81761.74;1485"
                                     +"127:53096.13;0462789:283259.72;2137926:1847.95;2142690:80330.25;0734626:35937.79;0085222:101329.45;077372"
                                     +"9:17091.94;2417735:251761.34;1508772:126595.04;1776006:277771.56;1787942:113312.80;2073795:139842.54;2009"
                                     +"829:71049.73;0749700:243430.14;0093923:315689.91;1375847:164403.46;1368997:264155.20;1628290:197111.98;08"
                                     +"26912:14386.91;1439616:208442.83;1575715:62488.27;0479013:53440.28;2083681:155351.42;2454979:140373.02;07"
                                     +"58115:5800.62;0729377:64786.79;1690240:58995.50;1777475:53336.84;0478147:221793.10;2411330:156102.88;2488"
                                     +"582:185746.34;2431174:135392.63;0077191:166205.23;2246817:134714.01;0889733:197578.93;0836103:117924.72;0"
                                     +"992773:101858.49;2452996:112981.61;2481317:71187.59;1741281:189438.22;1507847:132022.06;1803366:11486.36;");

		
		
		
		long start = new Date().getTime();
		PreAggregation preAggregation = new PreAggregation(select, groupingKeys,  false);
		
		PreAggRecord preAggRecord=null;
		
		for (int i = 0; i < 1000; i++) {
			
			preAggRecord = new PreAggRecord(tableRecordNew, preAggregation);
		}

		System.out.println("preAggRecord.size: "+preAggRecord.getRecords().size());
		preAggRecord.addRecord(tableRecord4);
//		System.out.println("preAggRecord.add: "+preAggRecord);
		try {
			System.out.println("preAggRecord: "+preAggRecord.buildAggRecord("C", "x1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			System.out.println("preAggRecord trans: "+preAggRecord.transform());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(">Time: "+(new Date().getTime()-start));
		
//		try {
//			preAggRecord.backTransform(preAggRecord.transform());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		System.out.println("preAggRecord back-trans: "+preAggRecord);
		
//		System.out.println("preAggRecord result: "+preAggRecord.buildAggRecord("C", "x1"));
//		System.out.println("preAggRecord back: "+preAggRecord.transform());
		
		System.out.println("-------------");
		

		List<ColumnName> joinColumns = new ArrayList<ColumnName>();
		joinColumns.add(new ColumnName("q1_V0.c1"));
		joinColumns.add(new ColumnName("q1_V1.d1"));
		
//		String[] columns = {"q1_V0.c1","q1_V0.c2","q1_V1.c2", "q1_V1.c3"};
		
//		TableRecord tableRecordJoin = generateTableRecord("A", "x1", "colfam_A", "k1","f1", "colfam_B", "l1","15", "colfam_C", "m1","10");

//		TableRecord tableRecordNew1 = TableRecord.generateTableRecord("A", "x1", "q1_V0", "k1:c2=10,c3=15;k2:c2=5,c3=8;k3:c2=7,c3=18;", "q1_V1", "l1:d2=12,d3=15;l2:d2=5,d3=8;l3:d2=7,d3=18;");
		TableRecord tableRecordNew1 = TableRecord.generateTableRecord(null, "x0173", "c1", "q1_V0{k09069:c1=x0173,c2=056;}|q1_V1{l08069:d1=x0173,d2=088;l0500:d1=x0173,d2=098;}");
		
		ReverseJoin reverseJoin= new ReverseJoin(joinColumns, false);
		
//		tableRecordJoin, 
		ReverseJoinRecord reverseJoinRecord = new ReverseJoinRecord(tableRecordNew1, reverseJoin);
		
//		tableRecordA = TableRecord.generateTableRecord("q1_V1", "l4", "d1", "x1", "d2", "11", "d3", "13");
//		reverseJoinRecord.addRecord(tableRecordA);
		
		tableRecordB = TableRecord.generateTableRecord("q1_V0", "k1", "c1", "x1", "c2", "11", "c3", "13");

		BaseTableUpdate btu = new BaseTableUpdate();
		btu.setBaseRecord(tableRecordB);
		btu.setOperationType("Put");
		
		
//		reverseJoinRecord.removeTable(btu.getBaseRecord().getTableName());
//		
//		reverseJoinRecord.addRecord(btu.getBaseRecord());
		
//		reverseJoinRecord.addRecord(tableRecordA2);
//		reverseJoinRecord.addRecord(tableRecordB);
//		reverseJoinRecord.addRecord(tableRecordC);
		
		System.out.println("reverseJoinRecord: "+reverseJoinRecord);
		TableRecord joinRec;
		try {
			joinRec = reverseJoinRecord.transform();
			System.out.println("reverseJoinRecord trans: "+joinRec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		joinRec.setTableName(null);
		
		try {
			reverseJoinRecord.backTransform(reverseJoinRecord.transform());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("reverseJoinRecord back-trans: "+reverseJoinRecord);
//		reverseJoinRecord.backTransform(joinRec);
//		System.out.println("reverseJoinRecord back: "+reverseJoinRecord);
		List<TableRecord> joinRecords = reverseJoinRecord.buildJoinRecords();
		System.out.println("joinRecords:"+joinRecords);
	

		List<BaseTableUpdate> outputBtus=new ArrayList<BaseTableUpdate>();
		
		if(joinRecords != null){
			for (TableRecord joinRecord : joinRecords) {
				
				
				BaseTableUpdate btuCopy = btu.copy();
				joinRecord.setTableName("q0_3");
				btuCopy.setRecord(btu.getOperationType(), joinRecord);

				outputBtus.add(btuCopy);
				
				System.out.println("btuCopy: "+btuCopy);
			}
		}	
		
		
//		System.out.println("preAggRecord back: "+preAggRecord.transform());
		
		
		
		
		
//		ReverseJoinRecord reverseJoinRecord2 = new ReverseJoinRecord(reverseJoin);
//		
//		List<TableRecord> records = new ArrayList<TableRecord>();
//		records.add(tableRecord);
//		
//		reverseJoinRecord2.getTableRecords().put(tableRecord.getTableName(), records);
//		System.out.println("reverseJoinRecord2: "+reverseJoinRecord2.transform());

		
		
		
		
		
		
	}


	
	
}
