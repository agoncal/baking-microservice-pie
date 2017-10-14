import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministratorRoutingModule } from './administrator-routing.module';
import { NumberComponent } from './number/number.component';
import { NumbersApi } from '../service/api/NumbersApi';
import { HttpModule } from '@angular/http';

@NgModule({
  imports: [
    CommonModule,
    AdministratorRoutingModule,
    HttpModule
  ],
  declarations: [NumberComponent],
  exports: [NumberComponent],
  providers: [NumbersApi],
})
export class AdministratorModule { }
