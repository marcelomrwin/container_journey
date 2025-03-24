import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../services/account.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Account} from '../../model/account.model';
import {NgIf} from '@angular/common';
import {Button} from 'primeng/button';
import {InputText} from 'primeng/inputtext';
import {Fluid} from 'primeng/fluid';
import {InputNumber} from 'primeng/inputnumber';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  imports: [
    ReactiveFormsModule,
    NgIf,
    InputText,
    Button,
    Fluid,
    FormsModule,
    InputNumber
  ],
  styleUrls: ['./account-form.component.css']
})
export class AccountFormComponent implements OnInit {
  accountForm!: FormGroup;
  accountId: number | null = null;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.accountForm = this.fb.group({
      accountNumber: ['', Validators.required],
      ownerName: ['', Validators.required],
      balance: [0, [Validators.required, Validators.min(0)]]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.accountId = Number(id);
      this.isEditMode = true;
      this.accountService.getAccountById(this.accountId).subscribe((data) => {
        this.accountForm.patchValue(data);
      });
    }
  }

  onSubmit(): void {

    if (this.accountForm.valid) {
      const accountData: Account = this.accountForm.value;

      if (this.isEditMode && this.accountId) {
        this.accountService.updateAccount(this.accountId, accountData).subscribe(() => {
          this.router.navigate(['/accounts']);
        });
      } else {
        this.accountService.createAccount(accountData).subscribe(() => {
          this.router.navigate(['/accounts']);
        });
      }
    }
  }

  goBack(): void {
    this.router.navigate(['/accounts']);
  }
}
