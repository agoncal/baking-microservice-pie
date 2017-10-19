import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministratorRoutingModule } from './administrator-routing.module';
import { NumberComponent } from './number/number.component';
import { HttpModule } from '@angular/http';
import { NumbersApi } from '../shared/api/NumbersApi';
import { SwaggerComponent } from './swagger/swagger.component';

@NgModule({
  imports: [
    CommonModule,
    AdministratorRoutingModule,
    HttpModule
  ],
  declarations: [
    NumberComponent,
    SwaggerComponent
  ],
  exports: [
    NumberComponent,
    SwaggerComponent
  ],
  providers: [
    NumbersApi
  ],
})
export class AdministratorModule {
}
