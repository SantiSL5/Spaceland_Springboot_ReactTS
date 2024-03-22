import { ErrorMessage } from "@hookform/error-message";
import { useForm } from 'react-hook-form';

interface IFormInputs {
    email: string,
    password: string,
}

const LoginForm = ({ login }: any) => {

    const {
        register,
        formState: { errors },
        handleSubmit,
        setValue
    } = useForm<IFormInputs>({
        criteriaMode: "all"
    });



    const onSubmit = (data: IFormInputs) => {
        login(data);
    };

    return (
        <div className="container mt-3">
            <form onSubmit={handleSubmit(onSubmit)} className="d-flex justify-content-center">
                <div className="d-flex flex-column col-5 text-start">
                    <label htmlFor="Email" className="form-label text-dark">Email:
                        <input
                            id="email"
                            className="form-control mt-2"
                            {...register("email", {
                                required: "required",
                                pattern: {
                                    value: /\S+@\S+\.\S+/,
                                    message: "Entered value does not match email format"
                                }
                            })}
                            type="email"
                        />
                        {errors.email && <span role="alert">{errors.email.message}</span>}
                    </label>
                    <label htmlFor="Password" className="form-label text-dark">Password:
                        <input
                            id="password"
                            className="form-control mt-2"
                            {...register("password", {
                                required: "required",
                                minLength: {
                                    value: 8,
                                    message: "Min length is 8"
                                }
                            })}
                            type="password"
                        />
                        {errors.password && <span role="alert">{errors.password.message}</span>}
                    </label>
                    <button type="submit" className="bpurple mt-4">Login</button>
                </div>
            </form>


        </div >
    );
}

export default LoginForm;