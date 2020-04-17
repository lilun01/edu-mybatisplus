package com.nature.edu.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Description: 代码生成器 ，指定表名自动生成dao层、service层，controller层
 *               dao层包括mapper的java类、xml文件， servier包括接口类和空实现类，
 *               controller包括对应的controller空实现 对应的模块响应修改包名 以及数据库连接信息即可，指定要生成的表名
 *
 * @author: wangck
 * @date: 2018/8/2 12:57
 */
public class AutoGeneratorHelper {

	public static void main(String[] args) {
		String packageName = "com.nature.edu";
		boolean serviceNameStartWithI = true;
		generateByTables(serviceNameStartWithI, packageName, "bas_user");
	}

	private static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		String url = "jdbc:mysql://localhost:3306/nature-edu?useSSL=false";
		String username = "root";
		String password = "root";
		String driverClassName = "com.mysql.jdbc.Driver";

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(url).setUsername(username).setPassword(password).setDriverName(driverClassName);
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setNaming(NamingStrategy.underline_to_camel)
				.setInclude(tableNames);
		// 修改替换成你需要的表名，多个表名传数组
		config.setActiveRecord(false).setAuthor("auto").setOutputDir("D:\\mumu-gen").setFileOverride(true);
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}
		new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig).setStrategy(strategyConfig)
				.setPackageInfo(new PackageConfig().setParent(packageName).setEntity("entity").setMapper("dao").setService("service")
						.setController("controller").setXml("mapper.edu.mysql"))
				.execute();
	}

}
