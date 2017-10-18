# Store

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.4.7.

## Angular CLI commands

### Initiliaze

```
$ ng new store --directory angular --prefix store --routing true --skip-tests true --inline-style true
```

### Twitter Bootstrap

* Install Bootstrap dependency `yarn add bootstrap@4.0.0-beta`, `yarn add jquery@3.2.1`, `yarn add popper.js@1.11.0`, `yarn add font-awesome@4.7.0`
* In `angular-cli.json` file add :
```
"styles": [
  "../node_modules/bootstrap/dist/css/bootstrap.css",
  "jumbotron.css",
  "styles.css"
],
"scripts": [
  "../node_modules/jquery/dist/jquery.slim.js",
  "../node_modules/popper.js/dist/popper.js",
  "../node_modules/bootstrap/dist/js/bootstrap.js"
],
```
* In `app.module.ts`
```
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
  imports: [
    NgbModule.forRoot()
  ],
```

### Admin module

```
$ ng generate module administrator --spec false --routing true --module app

```

### Admin components

```
$ ng generate component administrator/number --spec false --module administrator --export true --inline-style true
```

### Swagger Codegen

```
$ swagger-codegen generate -i ../../services/number-api/src/main/webapp/swagger.json -l typescript-angular2 -o src/app/shared
swagger-codegen generate -i ../../services/cd-api/src/main/webapp/swagger.json -l typescript-angular2 -o src/app/shared
```

## Angular CLI documentation

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

### Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

### Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

### Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
