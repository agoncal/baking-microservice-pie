import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NumberComponent } from './number/number.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookFormComponent } from './book-form/book-form.component';
import { BookListComponent } from './book-list/book-list.component';

const routes: Routes = [
  {path: 'book-detail', component: BookDetailComponent},
  {path: 'book-form', component: BookFormComponent},
  {path: 'book-list', component: BookListComponent},
  {path: 'numbers', component: NumberComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministratorRoutingModule { }
