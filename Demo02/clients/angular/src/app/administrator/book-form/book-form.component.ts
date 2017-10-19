import { Component, OnDestroy, OnInit } from '@angular/core';
import { Book } from '../../shared/model/Book';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BooksApi } from '../../shared/api/BooksApi';
import { Observable } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';
import { BookPopupService } from '../book-popup.service';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'store-book-form',
  templateUrl: './book-form.component.html',
  styles: []
})
export class BookFormComponent implements OnInit {

  book: Book;
  isSaving: boolean;

  constructor(public activeModal: NgbActiveModal,
              private booksApi: BooksApi,
              private eventManager: JhiEventManager
  ) {
  }

  ngOnInit() {
    this.isSaving = false;
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  save() {
    this.isSaving = true;
    if (this.book.id !== undefined) {
      this.subscribeToSaveResponse(
        this.booksApi.update(this.book));
    } else {
      this.subscribeToSaveResponse(
        this.booksApi.create(this.book));
    }
  }

  private subscribeToSaveResponse(result: Observable<Book>) {
    result.subscribe((res: Book) =>
      this.onSaveSuccess(res), (res: Response) => this.onSaveError());
  }

  private onSaveSuccess(result: Book) {
    this.eventManager.broadcast({ name: 'bookListModification', content: 'OK'});
    this.isSaving = false;
    this.activeModal.dismiss(result);
  }

  private onSaveError() {
    this.isSaving = false;
  }
}

@Component({
  selector: 'store-book-form-popup',
  template: ''
})
export class BookFormPopupComponent implements OnInit, OnDestroy {

  routeSub: any;

  constructor(private route: ActivatedRoute,
              private bookPopupService: BookPopupService) {
  }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe((params) => {
      if (params['id']) {
        this.bookPopupService
          .open(BookFormComponent as Component, params['id']);
      } else {
        this.bookPopupService
          .open(BookFormComponent as Component);
      }
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
