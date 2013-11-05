AIC13G3P3
=========

# Get up and running

* Setup Mysql, create DB AIC13G3P3 (utf8_bin) and User AIC13G3P3 with password AIC13G3P3
* Copy file '/src/main/webapp/WEB-INF/classes/aic13g3p3.proerties.template' to '/src/main/webapp/WEB-INF/classes/aic13g3p3.properties' (please don't check in the *.properties file)
* run 'mvn clean compile war:war exec:exec'
* Start your browser and type 'http://localhost:8080/G3P3/'

* To stop the system write "end" into the java-console. (However this doesn't work properly, once cloud scale ist started. Yuu have to manually kill the processes then :/ )

# Get Help
More Infos in the [wiki](https://github.com/tarator/AIC13G3P3/wiki)
