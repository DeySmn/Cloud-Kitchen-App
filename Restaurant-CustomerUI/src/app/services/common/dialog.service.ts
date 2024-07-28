import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(public dialog: MatDialog) { }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string, height: string, width: string, component: any) {
    return this.dialog.open(component, {
      height: height,
      width: width,
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        title: 'Confirm',
        message: 'Are you sure you want to cancel this order?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No'
        }
      }
    });
  }
}
