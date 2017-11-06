import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../shared/auth.service';

@Component({
  selector: 'store-login',
  templateUrl: './login.component.html',
  styles: []
})
export class LoginComponent implements OnInit {

  user: string;
  password: string;

  constructor(public router: Router,
              private authService: AuthService,
              private route: ActivatedRoute,
              private eventManager: JhiEventManager) {
  }

  ngOnInit() {
  }

  cancel() {
    this.router.navigate(['/']);
  }

  login() {
    this.authService
      .login(this.user, this.password)
      // .mergeMap(jwt => this.route.queryParams)
      // .map(qp => qp['redirectTo'])
      .subscribe(redirectTo => {
        if (this.authService.isLoggedIn) {
          let url = redirectTo ? [redirectTo] : ['/'];
          this.router.navigate(url);
        }
      });
  }
}
