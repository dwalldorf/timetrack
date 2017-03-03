export class WorklogEntryModel {

    public id: string;
    public userId: string;
    public customer: string;
    public project: string;
    public task: string;
    public comment: string;
    public start: any;
    public stop: any;
    public duration: number;
}