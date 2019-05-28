package Data;

public class Employees {
    private int id;
    private String FirstName;
    private String SecondName;
    private String Phone;
    private int Count;
    private String Position;
    private String Photo;
    private String Login;
    private String Password;

    public  Employees()
    {
    }
    /*public  Employees(int id,String Name,String Surname,String Phn, int CNT,String Pos,String photo1,String log,String Pass)
    {
        this.id = id;
        FirstName =Name;
        SecondName = Surname;
        Phone = Phn;
        Count = CNT;
        Position = Pos;
        Login = log;
        Photo = photo1;
        Password = Pass;
    }*/

    public int getId() {
        return id;
    }


    public String getFirstName() {
        return FirstName;
    }

    public String getLogin() {
        return Login;
    }

    public int getCount() {
        return Count;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPhoto() {
        return Photo;
    }

    public String getPosition() {
        return Position;
    }

    public String getSecondName() {
        return SecondName;
    }

    public static Builder newBuilder(){
        return new Employees().new Builder();
    }



    public class Builder {
        private int id;
        private String FirstName;
        private String SecondName;
        private String Phone;
        private int Count;
        private String Position;
        private String Photo;
        private String Login;
        private String Password;


        private Builder() {
            // private constructor
        }


        public Builder setSecondName(String secondName) { Employees.this.SecondName = secondName;
            return this; }

        public Builder setCount(int count) {
            Employees.this.Count = count;
            return this;
        }

        public Builder setId(int id) {
            Employees.this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            Employees.this.FirstName = firstName;
            return this;
        }

        public Builder setLogin(String login) {
            Employees.this.Login = login;
            return this;
        }

        public Builder setPassword(String password) {
            Employees.this.Password = password;
            return this;
        }

        public Builder setPhone(String phone) {
            Employees.this.Phone = phone;
            return this;
        }

        public Builder setPhoto(String photo) {
            Employees.this.Photo = photo;
            return this;
        }

        public Builder setPosition(String position) {
            Employees.this.Position = position;
            return this;
        }

        public Employees build(){
            return Employees.this;
        }
    }



}
