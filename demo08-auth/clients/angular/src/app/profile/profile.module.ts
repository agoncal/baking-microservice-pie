import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { HttpModule } from '@angular/http';
import { NgbActiveModal, NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { JhiEventManager } from 'ng-jhipster';
import { AuthService } from '../shared/auth.service';

@NgModule({
  imports: [
    CommonModule,
    ProfileRoutingModule,
    HttpModule,
    NgbModule.forRoot(),
    FormsModule,
  ],
  declarations: [
    LoginComponent,
    LogoutComponent
  ],
  entryComponents: [
    LoginComponent,
    LogoutComponent
  ],
  exports: [
    LoginComponent,
    LogoutComponent
  ],
  providers: [
    NgbModal,
    AuthService,
    NgbActiveModal,
    JhiEventManager
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProfileModule {
}
