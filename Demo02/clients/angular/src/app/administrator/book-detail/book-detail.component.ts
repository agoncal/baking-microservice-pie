import { Component, OnInit } from '@angular/core';
import { Book } from '../../shared/model/Book';
import { ActivatedRoute } from '@angular/router';
import { BooksApi } from '../../shared/api/BooksApi';

@Component({
  selector: 'store-book-detail',
  templateUrl: './book-detail.component.html',
  styles: []
})
export class BookDetailComponent implements OnInit {

  book: Book;

  constructor(private booksApi: BooksApi,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
  }

  load(id) {
    this.booksApi.findById(id).subscribe((book) => {
      this.book = book;
    });
  }

  previousState() {
    window.history.back();
  }
}
