import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministratorRoutingModule } from './administrator-routing.module';
import { NumberComponent } from './number/number.component';
import { HttpModule } from '@angular/http';
import { NumbersApi } from '../shared/api/NumbersApi';
import { BookListComponent } from './book-list/book-list.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookFormComponent } from './book-form/book-form.component';
import { BooksApi } from '../shared/api/BooksApi';

@NgModule({
  imports: [
    CommonModule,
    AdministratorRoutingModule,
    HttpModule
  ],
  declarations: [
    NumberComponent,
    BookListComponent,
    BookDetailComponent,
    BookFormComponent
  ],
  exports: [
    NumberComponent,
    BookListComponent,
    BookDetailComponent,
    BookFormComponent
  ],
  providers: [
    NumbersApi,
    BooksApi
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdministratorModule {
}
