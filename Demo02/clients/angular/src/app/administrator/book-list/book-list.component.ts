import { Component, OnInit } from '@angular/core';
import { Book } from '../../shared/model/Book';
import { BooksApi } from '../../shared/api/BooksApi';

@Component({
  selector: 'store-book-list',
  templateUrl: './book-list.component.html',
  styles: []
})
export class BookListComponent implements OnInit {

  books: Book[];

  constructor(private booksApi: BooksApi) { }

  ngOnInit() {
    this.booksApi.findAll().subscribe(books => this.books = books);
  }
}
