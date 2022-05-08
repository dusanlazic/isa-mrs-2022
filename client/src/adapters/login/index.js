import { post } from "../xhr";
import { saveToken } from '../../contexts'

export function login(data, redirect) {
  post('/api/auth/login', 
  {
    username: data.email,
    password: data.password,
  })
  .then(res => {
    if (res.data) {
      saveToken(res.data.accessToken);
      redirect();
    }
  })
}