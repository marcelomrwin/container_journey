export class Account {
  id: number;
  accountNumber: string;
  ownerName: string;
  balance: number;

  constructor(id: number, accountNumber: string, ownerName: string, balance: number = 0) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.ownerName = ownerName;
    this.balance = balance;
  }
}
