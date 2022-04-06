# openapi-generator-showcase Project
The [OpenAPI Specification](https://swagger.io/docs/specification/about/) (OAS) gives a standard on how to create a specification for a REST api. This is the de-facto way of documenting your api. Often you would want to create your specification before you actually start implementing your services to ensure you meet the requirements. It is also immensely useful when exposing your services, it shows your consumers precisely how to use your api. 

Since such an API specification fully describes the in- and output of an API this actually allows us to automatically generate the code needed to consume a service. The [OpenAPI generator](https://openapi-generator.tech/) is a massive project that can perform this code generation for a wide variety of languages and libraries. You can just input your api spec, select your language+library, and it will generate models and api classes that you can use in your code. 

## OpenAPI Generator maven plugin
For Java (and other JVM languages) there is a [maven plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin) available that will perform this code generation upon compilation. To set this up simply add below plugin to your `pom.xml`:
```xml
<plugin>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-generator-maven-plugin</artifactId>
  <version>5.4.0</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <!-- Location of your openapi.yml -->
        <inputSpec>${project.basedir}/src/main/resources/api-specs/pet-store.yaml</inputSpec>
        <!-- The specific type of client/server you want to generate -->
        <generatorName>java</generatorName>
        <library>microprofile</library>
        <!-- Specifying what to generate and where -->
        <generateApiTests>false</generateApiTests>
        <generateModelTests>false</generateModelTests>
        <apiPackage>org.acme.gen.api</apiPackage>
        <modelPackage>org.acme.gen.model</modelPackage>
        <!-- Adding your own templates -->
        <templateDirectory>${project.basedir}/src/main/resources/templates/client/java/microprofile</templateDirectory>
      </configuration>
    </execution>
  </executions>
</plugin>
```
This specifically will create a [MicroProfile REST client](https://download.eclipse.org/microprofile/microprofile-rest-client-2.0/microprofile-rest-client-spec-2.0.html) (based on the JAX-RS spec) and models for the OpenAPI spec that is provided at `src/main/resources/api-specs/pet-store.yaml`. For a full list of the generators and their libraries [see here](https://openapi-generator.tech/docs/generators). 

The OpenAPI generator uses [Mustache](https://mustache.github.io/mustache.5.html) as its templating engine to generate the files. Using the `templateDirectory` folder you can provide a location for custom templates which, if present, override the default template files of your chosen library (see [here](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources/Java/libraries/microprofile) for the microprofile templates). 

This client is also used by the [Quarkus REST client extension](https://quarkus.io/guides/rest-client). We are developing a Quarkus extension for the OpenAPI generator here: [Quarkus - OpenAPI Generator](https://github.com/quarkiverse/quarkus-openapi-generator). It uses Qute, a custom templating language created for Quarkus, and it provides templates that are closer to the default quarkus libraries (e.g. it uses Jackson as serializer, and RESTEasy for multipart instead of Apache CXF as used by above microprofile generator) and other features such as [Circuit Breaker](https://quarkus.io/guides/smallrye-fault-tolerance#adding-resiliency-circuit-breaker). Feel free to help out!

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
Check out the tests to see how it's used

