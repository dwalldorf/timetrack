import {UserSettings} from "./user.settings";

export class User {

    public id: string;
    public username: string;
    public email: string;
    public password: string;
    public registered: number;
    public userSettings: UserSettings;

}