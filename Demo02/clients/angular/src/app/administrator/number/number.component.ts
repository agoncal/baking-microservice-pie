import { Component, OnInit } from '@angular/core';
import { NumbersApi } from '../../shared/api/NumbersApi';

@Component({
  selector: 'store-number',
  templateUrl: './number.component.html',
  styles: []
})
export class NumberComponent implements OnInit {

  private bookNumber: string;

  constructor(private numberApi: NumbersApi) { }

  ngOnInit() {
  }

  generateBookNumber() {
    // this.bookNumber = "toto";
      this.numberApi.generateBookNumber().subscribe(bookNumber => this.bookNumber = bookNumber);
  }

}
