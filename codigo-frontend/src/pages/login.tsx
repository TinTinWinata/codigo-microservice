import useCodigo from "../context/codigo-context";
import { User } from "../types/user";

export default function Login() {
  const { login } = useCodigo();
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!e.target.name || e.target.phoneNumber) {
      return;
    }
    const user: User = {
      name: e.target.name.value,
      phoneNumber: e.target.phoneNumber.value,
    };
    login(user);
  };
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input name="name" type="text" placeholder="Name" />
        <input name="phoneNumber" type="text" placeholder="Phone Number" />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}
