import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NumberComponent } from './number/number.component';

const routes: Routes = [
  {path: 'number', component: NumberComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministratorRoutingModule { }
