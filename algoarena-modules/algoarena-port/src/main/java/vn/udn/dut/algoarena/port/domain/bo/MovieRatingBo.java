package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.MovieRating;

/**
 * Movie rating business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@AutoMapper(target = MovieRating.class, reverseConvertGenerate = false)
public class MovieRatingBo {

	private Long id;

	private String tenantId;

	private Long movie_id;

	private Long starNumber;

	private Long customerId;
}
