import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CustomerModule } from './customer/customer.module';
import { AdministratorModule } from './administrator/administrator.module';
import { AnonymousModule } from './anonymous/anonymous.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule.forRoot(),
    CustomerModule,
    AdministratorModule,
    AnonymousModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
