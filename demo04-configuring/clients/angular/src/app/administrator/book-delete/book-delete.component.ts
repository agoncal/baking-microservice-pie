import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Book } from '../../shared/model/Book';
import { BooksApi } from '../../shared/api/BooksApi';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BookPopupService } from '../book-popup.service';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'store-book-delete',
  templateUrl: './book-delete.component.html',
  styles: []
})
export class BookDeleteComponent {

  book: Book;

  constructor(private booksApi: BooksApi,
              public activeModal: NgbActiveModal,
              private eventManager: JhiEventManager) {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.booksApi._delete(id).subscribe((response) => {
      this.eventManager.broadcast({
        name: 'bookListModification',
        content: 'Deleted an book'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'store-book-delete-popup',
  template: ''
})
export class BookDeletePopupComponent implements OnInit, OnDestroy {

  routeSub: any;

  constructor(private route: ActivatedRoute,
              private bookPopupService: BookPopupService) {
  }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe((params) => {
      this.bookPopupService
        .open(BookDeleteComponent as Component, params['id']);
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
