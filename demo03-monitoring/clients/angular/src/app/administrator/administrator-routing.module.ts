import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NumberComponent } from './number/number.component';
import { SwaggerComponent } from './swagger/swagger.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookFormPopupComponent } from './book-form/book-form.component';
import { BookListComponent } from './book-list/book-list.component';
import { BookDeletePopupComponent } from './book-delete/book-delete.component';

const routes: Routes = [
  {path: 'book-detail/:id', component: BookDetailComponent},
  {path: 'book-list', component: BookListComponent},
  {path: 'numbers', component: NumberComponent},
  {path: 'swagger', component: SwaggerComponent}
];

export const popupRoute: Routes = [
  {path: 'book-form', component: BookFormPopupComponent, outlet: 'popup'},
  {path: 'book-form/:id/edit', component: BookFormPopupComponent, outlet: 'popup'},
  {path: 'book-delete/:id/delete', component: BookDeletePopupComponent, outlet: 'popup'}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    RouterModule.forChild(popupRoute)
  ],
  exports: [RouterModule]
})
export class AdministratorRoutingModule {
}
