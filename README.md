AIC13G3P3
=========

# Get up and running

* Setup Mysql, create DB AIC13G3P3 (utf8_bin) and User AIC13G3P3 with password AIC13G3P3
* Copy file '/src/main/resources/aic13g3p3.proerties.template' to '/src/main/resources/aic13g3p3.properties' (please don't check in the *.properties file)
* You have to start webapp and cloudscale-app seperately:
* Start Jetty and webapp: run 'mvn compile jetty:run'
* Start cloudscale app: run 'mvn exec:exec'
* Start your browser and type 'http://localhost:8080/G3P3/'
* To stop the system write "end" into the java-console of the cloudscale-app.

# Discussion
* Goole group: https://groups.google.com/forum/#!forum/aic13g3p3
# Get Help
More Infos in the [wiki](https://github.com/tarator/AIC13G3P3/wiki)
