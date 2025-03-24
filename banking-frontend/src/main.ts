import {bootstrapApplication} from '@angular/platform-browser';
import {appConfig} from './app/app.config';
import {AppComponent} from './app/app.component';
import {environment} from './environments/environment';

fetch('/assets/config.json')
  .then(res => res.json())
  .then((config) => {
    console.log(config);
    environment.apiUrl = config.apiUrl || environment.apiUrl;
    console.log('apiUrl='+ environment.apiUrl);
    bootstrapApplication(AppComponent, appConfig)
      .catch((err) => console.error(err));
  })
  .catch((err) => {
  console.error('Error loading config.json', err);
  console.warn('Loading default configurations');
  bootstrapApplication(AppComponent, appConfig)
    .catch((err) => console.error(err));
});


