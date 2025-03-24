import { Routes } from '@angular/router';
import { AccountListComponent } from './components/account-list/account-list.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { AccountFormComponent } from './components/account-form/account-form.component';

export const routes: Routes = [
  { path: '', redirectTo: '/accounts', pathMatch: 'full' },
  { path: 'accounts', component: AccountListComponent },
  { path: 'accounts/new', component: AccountFormComponent },
  { path: 'accounts/edit/:id', component: AccountFormComponent },
  { path: 'accounts/:id', component: AccountDetailsComponent },
  { path: '**', redirectTo: '/accounts' }
];
