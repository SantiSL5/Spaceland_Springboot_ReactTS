import userConsumer from "./userConsumer";
import jwtConsumer from "./jwtConsumer";

const consumers: any = {
    ApiUser: { ...userConsumer },
    ApiJwt: { ...jwtConsumer },
}

export default consumers;

