import { invalidateSession, invalidateToken } from "../contexts";

export function logout () {
  invalidateSession();
  invalidateToken();
}