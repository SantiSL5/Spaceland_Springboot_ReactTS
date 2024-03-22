import consumers from "../../core/consumers/index";


const consume = (consumer: string, method: string, data?: any) => {
    return consumers[consumer][method](data);
}

export default consume;
