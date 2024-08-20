export class Todo {
    id: string;
    title: string;
    description: string;
    startDate: Date;
    endDate: Date;
    status: string;
    effortRequired: number;
    userId: string;
    category: string;
    reminderDate?: Date;
    eventName?: string;
    isFavorite?: boolean;


    isEndDateValid(): boolean {
        let isValidDate = this.endDate > this.startDate;
        if (this.reminderDate) {
            isValidDate = this.reminderDate <= this.endDate;
        }
        return isValidDate;
    }
}