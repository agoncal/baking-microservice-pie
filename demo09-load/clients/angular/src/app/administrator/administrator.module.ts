import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministratorRoutingModule } from './administrator-routing.module';
import { NumberComponent } from './number/number.component';
import { HttpModule } from '@angular/http';
import { NumbersApi } from '../shared/api/NumbersApi';
import { SwaggerComponent } from './swagger/swagger.component';
import { BookListComponent } from './book-list/book-list.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookFormComponent, BookFormPopupComponent } from './book-form/book-form.component';
import { BooksApi } from '../shared/api/BooksApi';
import { BookDeleteComponent, BookDeletePopupComponent } from './book-delete/book-delete.component';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal, NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BookPopupService } from './book-popup.service';
import { JhiEventManager } from 'ng-jhipster';

@NgModule({
  imports: [
    CommonModule,
    AdministratorRoutingModule,
    HttpModule,
    NgbModule.forRoot(),
    FormsModule,
  ],
  declarations: [
    NumberComponent,
    SwaggerComponent,
    BookListComponent,
    BookDetailComponent,
    BookFormComponent,
    BookFormPopupComponent,
    BookDeleteComponent,
    BookDeletePopupComponent
  ],
  entryComponents: [
    BookFormComponent,
    BookFormPopupComponent,
    BookDeleteComponent,
    BookDeletePopupComponent
  ],
  exports: [
    NumberComponent,
    SwaggerComponent,
    BookListComponent,
    BookDetailComponent,
    BookFormComponent,
    BookDeleteComponent
  ],
  providers: [
    NumbersApi,
    BooksApi,
    BookPopupService,
    NgbModal,
    NgbActiveModal,
    JhiEventManager
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdministratorModule {
}
