import {AfterViewInit, Component, ElementRef} from '@angular/core';

import SwaggerUI from 'swagger-ui';

@Component({
  selector: 'app-swagger',
  templateUrl: './swagger.component.html',
  styles: []
})
export class SwaggerComponent implements AfterViewInit {

  constructor(private el: ElementRef) {
  }

  ngAfterViewInit() {
    const ui = SwaggerUI({
      url: 'http://localhost:8081/book-api/swagger.json',
      domNode: this.el.nativeElement.querySelector('.swagger-container'),
      deepLinking: true,
      presets: [
        SwaggerUI.presets.apis
      ],
    });
  }

}
