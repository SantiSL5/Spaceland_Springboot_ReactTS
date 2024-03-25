import { ErrorMessage } from "@hookform/error-message";
import { useForm } from 'react-hook-form';

interface IFormInputs {
    id: number,
    username: string,
    email: string,
    password: string,
    role: string,
    enabled: boolean
}

const CreateUpdate = ({ createUser, operation, updateData, updateUser, changeForm }: any) => {

    const {
        register,
        formState: { errors },
        handleSubmit,
        setValue
    } = useForm<IFormInputs>({
        criteriaMode: "all"
    });

    if (updateData) {
        setValue("id", updateData.id)
        setValue("username", updateData.username)
        setValue("email", updateData.email)
        setValue("role", updateData.role)
        setValue("enabled", updateData.enabled)
    }

    if (operation === "create" && updateData !== undefined) {
        setValue("username", "")
        setValue("email", "")
        setValue("password", "")
        setValue("role", "")
        setValue("enabled", true)
    }


    const onSubmit = (data: IFormInputs) => {
        operation === "create" ? createUser(data) : updateUser(data);   
    };

    return (
        <div className="container mt-4">
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="mb-2">
                    <label htmlFor="username" className="form-label text-dark">Username:
                        <input id="username" type="text" className="form-control mt-2"
                            {...register("username", {
                                required: "This input is required.",
                                maxLength: {
                                    value: 32,
                                    message: "This input exceed maxLength."
                                }
                            })}
                        />
                        <ErrorMessage
                            errors={errors}
                            name="username"
                            render={({ messages }) => {
                                return messages
                                    ? Object.entries(messages).map(([type, message]) => (
                                        <p key={type}>{message}</p>
                                    ))
                                    : null;
                            }}
                        />
                    </label>
                </div>
                <div className="mb-2">
                    <label htmlFor="email" className="form-label text-dark">Email:
                        <input id="email" type="text" className="form-control mt-2"
                            {...register("email", {
                                required: "This input is required.",
                                maxLength: {
                                    value: 32,
                                    message: "This input exceed maxLength."
                                },
                                pattern: {
                                    value: /\S+@\S+\.\S+/,
                                    message: "Entered value does not match email format"
                                }
                            })}
                        />
                        <ErrorMessage
                            errors={errors}
                            name="email"
                            render={({ messages }) => {
                                return messages
                                    ? Object.entries(messages).map(([type, message]) => (
                                        <p key={type}>{message}</p>
                                    ))
                                    : null;
                            }}
                        />
                    </label>
                </div>
                <div className="mb-2">
                    <label htmlFor="password" className="form-label text-dark">Password:
                        <input id="password" type="text" className="form-control mt-2"
                            {...register("password", {
                                maxLength: {
                                    value: 32,
                                    message: "This input exceed maxLength."
                                }
                            })}
                        />
                        <ErrorMessage
                            errors={errors}
                            name="password"
                            render={({ messages }) => {
                                return messages
                                    ? Object.entries(messages).map(([type, message]) => (
                                        <p key={type}>{message}</p>
                                    ))
                                    : null;
                            }}
                        />
                    </label>
                </div>

                <div className="mb-2">
                    <label htmlFor="role" className="form-label text-dark">Role:
                        <div>
                            <select className="mt-2 form-select"
                                {...register("role", {
                                    required: "This input is required.",
                                })}>            
                                <option value="ROLE_CLIENT">Client</option>
                                <option value="ROLE_WORKER">Worker</option>
                                <option value="ROLE_ADMIN">Admin</option>
                            </select>
                        </div>
                        <ErrorMessage
                            errors={errors}
                            name="role"
                            render={({ messages }) => {
                                return messages
                                    ? Object.entries(messages).map(([type, message]) => (
                                        <p key={type}>{message}</p>
                                    ))
                                    : null;
                            }}
                        />
                    </label>
                </div>

                <div className="mb-3 form-check">
                    <input type="checkbox" className="form-check-input" id="enabled" {...register("enabled", {})} />
                    <label className="form-check-label text-dark" htmlFor="enabled">Enabled</label>
                </div>


                {operation === "create"
                    ? <button type="submit" className="btn btn-success">Create</button>
                    : <div>
                        <button type="submit" className="btn btn-info">Update</button>
                        <button type="button" className="btn btn-success ms-3" onClick={
                            () => changeForm(null, "create")
                        }>Back to create</button>
                    </div>}
            </form>

        </div>
    );
}

export default CreateUpdate;