import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NumberComponent } from './number/number.component';
import { SwaggerComponent } from './swagger/swagger.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookFormComponent } from './book-form/book-form.component';
import { BookListComponent } from './book-list/book-list.component';

const routes: Routes = [
  {path: 'book-detail', component: BookDetailComponent},
  {path: 'book-form', component: BookFormComponent},
  {path: 'book-list', component: BookListComponent},
  {path: 'numbers', component: NumberComponent},
  {path: 'swagger', component: SwaggerComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministratorRoutingModule { }
