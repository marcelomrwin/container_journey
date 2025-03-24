import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {TableModule} from 'primeng/table';
import {CurrencyPipe} from '@angular/common';
import {Account} from '../../model/account.model';
import {ButtonDirective, ButtonModule} from 'primeng/button';

@Component({
  selector: 'app-account-list',
  imports: [TableModule, CurrencyPipe, ButtonDirective, ButtonModule],
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  accounts: Account[] = [];

  constructor(private accountService: AccountService, private router: Router) {
  }

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.accountService.getAccounts().subscribe((data) => {
      this.accounts = data;
    });
  }

  deleteAccount(id: number): void {
    if (confirm('Tem certeza que deseja excluir esta conta?')) {
      this.accountService.deleteAccount(id).subscribe(() => {
        this.loadAccounts(); // Atualiza a lista após a exclusão
      });
    }
  }

  goToDetails(id: number): void {
    this.router.navigate(['/accounts', id]);
  }

  goToCreate(): void {
    this.router.navigate(['/accounts/new']);
  }
}
