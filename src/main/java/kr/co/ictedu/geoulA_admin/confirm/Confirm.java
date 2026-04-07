package kr.co.ictedu.geoulA_admin.confirm;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import kr.co.ictedu.geoulA_admin.admins.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "confirmation")
@SequenceGenerator(name = "seq_confirm", sequenceName = "seq_confirm", allocationSize = 1)
public class Confirm {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_confirm")
    private Long confirm_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "req_admin_id", referencedColumnName = "admin_id")
    private Admin reqAdmin;
    private String req_detail;
    private String req_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "admin_id")
    private Admin supervisor;
}
