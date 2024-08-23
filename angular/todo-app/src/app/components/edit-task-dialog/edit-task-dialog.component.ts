import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-task-dialog',
  templateUrl: './edit-task-dialog.component.html',
  styleUrl: './edit-task-dialog.component.scss'
})
export class EditTaskDialogComponent {

  taskForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<EditTaskDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.taskForm = new FormGroup({
      startDate: new FormControl(null, Validators.required),
      endDate: new FormControl({ value: null, disabled: true }, [this.endDateValidator.bind(this)]),
      reminderDate: new FormControl({ value: null, disabled: true }, [this.reminderDateValidator.bind(this)])
    });

    this.taskForm.get('startDate').valueChanges.subscribe(value => {
      if (value) {
        this.taskForm.get('endDate').enable();
        this.taskForm.get('reminderDate').enable();
      } else {
        this.taskForm.get('endDate').disable();
        this.taskForm.get('reminderDate').disable();
      }
      this.taskForm.get('endDate').updateValueAndValidity();
      this.taskForm.get('reminderDate').updateValueAndValidity();
    });
  }

  endDateValidator(control: FormControl) {
    const startDate = this.taskForm?.get('startDate')?.value;
    if (startDate && control.value && control.value < startDate) {
      return { endDateInvalid: true };
    }
    return null;
  }

  reminderDateValidator(control: FormControl) {
    const startDate = this.taskForm?.get('startDate')?.value;
    const endDate = this.taskForm?.get('endDate')?.value;
    if (startDate && control.value && (control.value < startDate || (endDate && control.value > endDate))) {
      return { reminderDateInvalid: true };
    }
    return null;
  }

}
