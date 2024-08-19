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

    constructor(
        id: string,
        title: string,
        description: string,
        startDate: Date,
        endDate: Date,
        status: string,
        effortRequired: number,
        userId: string,
        category: string,
        reminderDate?: Date,
        eventName?: string
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.effortRequired = effortRequired;
        this.userId = userId;
        this.category = category;
        this.reminderDate = reminderDate;
        this.eventName = eventName;
    }

    isEndDateValid(): boolean {
        let isValidDate = this.endDate > this.startDate;
        if (this.reminderDate) {
            isValidDate = this.reminderDate <= this.endDate;
        }
        return isValidDate;
    }
}