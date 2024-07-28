import { NgModule } from "@angular/core";
import { ScreenLoaderComponent } from "./components/screen-loader/screen-loader.component";
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule } from "@angular/material/dialog";
import {
  SocialLoginModule,
  SocialAuthServiceConfig,
} from '@abacritt/angularx-social-login';
import { GoogleSigninButtonModule } from '@abacritt/angularx-social-login';
import {
  GoogleLoginProvider,
  FacebookLoginProvider,
  AmazonLoginProvider,
} from '@abacritt/angularx-social-login';
const fbLoginOptions = {
  scope: 'public_profile',
  locale: 'en_US',
  return_scopes: true,
  enable_profile_selector: true,
  version: 'v13.0',
};
@NgModule({
  declarations: [ScreenLoaderComponent],
  exports: [ScreenLoaderComponent, MatDialogModule,
    SocialLoginModule,
    GoogleSigninButtonModule,],
  imports: [MatDialogModule,
    SocialLoginModule,
    GoogleSigninButtonModule,],
  providers: [
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: true, disableClose: true } }, {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '84327651763-g6d73dojmkdb24qcnl9pniq0dd5rc5o8.apps.googleusercontent.com'
            ),
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider(
              '546100497690700',
              fbLoginOptions
            ),
          },
        ],
        onError: (error) => {
          console.error(error);
        },
      } as SocialAuthServiceConfig,
    },
  ],
})
export class SharedModule { }