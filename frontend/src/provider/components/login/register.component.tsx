import { ErrorMessage } from "@hookform/error-message";
import { useForm } from 'react-hook-form';

interface IFormInputs {
    email: string,
    username: string,
    password: string,
    repeatPassword: string,
}

const RegisterForm = ({ registerH }: any) => {

    const {
        register,
        formState: { errors },
        handleSubmit,
        getValues,
        setValue
    } = useForm<IFormInputs>({
        criteriaMode: "all"
    });


    const onSubmit = (data: IFormInputs) => {
        registerH(data);
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
                    <label htmlFor="Username" className="form-label text-dark">Username:
                        <input
                            id="username"
                            className="form-control mt-2"
                            {...register("username", {
                                required: "required",
                                minLength: {
                                    value: 3,
                                    message: "Min length is 3"
                                }
                            })}
                            type="text"
                        />
                        {errors.username && <span role="alert">{errors.username.message}</span>}
                    </label>
                    <label htmlFor="Password" className="form-label text-dark">Password:
                        <input
                            id="password"
                            className="form-control mt-2"
                            {...register("password", {
                                required: "required",
                                pattern: {
                                    value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,
                                    message: "Introduce a strong password"
                                }
                            })}
                            type="password"
                        />
                        {errors.password && <span role="alert">{errors.password.message}</span>}
                    </label>
                    <label htmlFor="RepeatPassword" className="form-label text-dark">Repeat Password:
                        <input
                            id="repeatPassword"
                            className="form-control mt-2"
                            {...register("repeatPassword", {
                                required: "required",
                                validate: (value) => {
                                    const { password } = getValues();
                                    return password === value || "Passwords should match";
                                }
                            })}
                            type="password"
                        />
                        {errors.repeatPassword && <span role="alert">{errors.repeatPassword.message}</span>}
                    </label>
                    <button type="submit" className="bpurple mt-4">Register</button>
                </div>
            </form>

        </div >
    );
}

export default RegisterForm;