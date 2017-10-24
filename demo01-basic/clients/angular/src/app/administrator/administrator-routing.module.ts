import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NumberComponent } from './number/number.component';
import { SwaggerComponent } from './swagger/swagger.component';

const routes: Routes = [
  {path: 'numbers', component: NumberComponent},
  {path: 'swagger', component: SwaggerComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministratorRoutingModule { }
