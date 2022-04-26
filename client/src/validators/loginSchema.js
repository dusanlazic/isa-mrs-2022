import * as yup from 'yup';

const schema = yup.object().shape({
  email: yup.string().email("Please enter a valid email.").required("Email is required."),
  password: yup.string().required("Password is required.").min(8, "Password must be 8 characters or longer."),
});

export default schema;