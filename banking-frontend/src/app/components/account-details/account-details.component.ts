import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../services/account.service';
import {Card} from 'primeng/card';
import {CurrencyPipe, NgIf} from '@angular/common';
import {Account} from '../../model/account.model';
import {PrimeTemplate} from 'primeng/api';
import {ButtonDirective} from 'primeng/button';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  imports: [
    Card,
    CurrencyPipe,
    NgIf,
    PrimeTemplate,
    ButtonDirective
  ],
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  account: Account = new Account(0,'','');

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.accountService.getAccountById(id).subscribe((data) => {
        this.account = data;
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/accounts']);
  }
}
