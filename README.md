# spring-data-simple-es6
Simple Es6 Api with Spring Data Style




# Appendix - Build the source

Install all (skip test)

```shell
mvn clean install -Dmaven.test.skip=true
```

Build all

```shell
mvn clean package
```

Build single project

* api

```shell
mvn -pl module/api clean package
# or
mvn --projects module/api clean package
```

* starter

```shell
mvn -pl module/starter clean package -am
# or
mvn --projects module/starter clean package --also-make
```
