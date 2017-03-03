export class DateUtil {

    public static toHtmlInputDate(date: Date): string {
        let month = this.prependZero(date.getMonth() + 1),
            day = this.prependZero(date.getDay());

        return date.getFullYear() + '-' + month + '-' + day;
    }

    public static toHtmlInputTime(date: Date): string {
        return this.prependZero(date.getHours()) + ':' + this.prependZero(date.getMinutes());
    }


    public static dateFormatJodaDateTime(date: any): string {
        return date.year + '-' + this.prependZero(date.monthOfYear) + '-' + this.prependZero(date.dayOfMonth);
    }

    public static timeFormat(date: any): string {
        return this.prependZero(date.hourOfDay) + ':' + this.prependZero(date.minuteOfHour);
    }

    public static dateTimeFormatJodaDateTime(date: any): string {
        return this.dateFormatJodaDateTime(date) + ' ' + this.timeFormat(date);
    }

    public static dateTimeHtmlInputFormat(date: any): string {
        return this.dateFormatJodaDateTime(date) + 'T' + this.timeFormat(date);
    }

    public static stringToJodaParseableDateTime(dateTime: string): string {
        return dateTime.replace('T', ' ');
    }

    public static toHours(minutes: number): number {
        minutes = minutes / 60;
        let multiplier = Math.pow(10, 2);
        return Math.round(minutes * multiplier) / multiplier;
    }

    private static prependZero(number: any): string {
        number = '' + number; // make sure it's a string
        if (number.length === 1) {
            number = '0' + number;
        }
        return number;
    }
}