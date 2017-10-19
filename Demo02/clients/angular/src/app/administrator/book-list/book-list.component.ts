import { Component, OnDestroy, OnInit } from '@angular/core';
import { Book } from '../../shared/model/Book';
import { BooksApi } from '../../shared/api/BooksApi';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';

@Component({
  selector: 'store-book-list',
  templateUrl: './book-list.component.html',
  styles: []
})
export class BookListComponent implements OnInit, OnDestroy  {

  books: Book[];
  eventSubscriber: Subscription;

  constructor(private booksApi: BooksApi,
              private eventManager: JhiEventManager) {
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBooks();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  loadAll() {
    this.booksApi.findAll().subscribe(books => this.books = books);
  }

  registerChangeInBooks() {
    this.eventSubscriber = this.eventManager.subscribe('bookListModification', (response) => this.loadAll());
  }
}
