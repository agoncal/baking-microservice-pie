import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministratorRoutingModule } from './administrator-routing.module';
import { NumberComponent } from './number/number.component';

@NgModule({
  imports: [
    CommonModule,
    AdministratorRoutingModule
  ],
  declarations: [NumberComponent],
  exports: [NumberComponent]
})
export class AdministratorModule { }
