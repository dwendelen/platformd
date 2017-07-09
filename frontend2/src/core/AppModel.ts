import {Summary} from "./summary";
import {Transaction} from "./transaction/transaction";

export class AppState {
    loginState: LoginState;
    accountSummaries: Summary[];
    bucketSummaries: Summary[];
    accountDetails: Transaction[];
}

export class LoginState {
    loggedIn: boolean;
    token: string | null;
    userId: string | null;
}